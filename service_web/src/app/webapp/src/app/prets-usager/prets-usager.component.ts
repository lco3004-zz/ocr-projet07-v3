/* *************************************************************
* Composant recherche les ouvrages prétés à l'usager connecté
* permet la prolongation d'un prêt
* si un pret est déja  prolongé le template ne permet pas de le selectionner
* à nouveau (le controle checkBox en regard de ce pret , est disabled et checked)
***************************************************************/
import {Component, OnDestroy, OnInit} from '@angular/core';
import {PretsUsagerService} from './prets-usager.service';
import {PretWeb} from '../model/PretWeb';
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {CollectionViewer, DataSource, SelectionModel} from "@angular/cdk/collections";
import {MatCheckboxChange} from "@angular/material/checkbox";


@Component({
  selector: 'app-prets-usager',
  templateUrl: './prets-usager.component.html',
  styleUrls: ['./prets-usager.component.css']
})
/*
Composant
 */
export class PretsUsagerComponent implements OnInit, OnDestroy {

  //table angular/materialdonc necessite datasource et displayed column
  // les champs de datasource doivent correspondre aux string de la variable
  //displayedColumns (même nom)
  private dataSource: PretDataSource;
  displayedColumns: string[] = ['select', 'auteur','titre', 'dateEmprunt'];

  //utilisé dans template pour contenir les prets sélectionnés en vue de prolongation
  selection = new SelectionModel<PretWeb>(true, []);

  //souscription aux observables qui gèrent la liste des prets
  // et l'état des controles "checkBox" du template
  //
  private subscribeListepret:Subscription;
  private subscribeProlongePret: Subscription;

  constructor(private pretsUsagerService: PretsUsagerService) {
  }
  /*
  appel à l'observable géré par le service prets-usager.service
  si ok, mise à jour da la datasource utilisée par le template
  les prets sont affichés
   */
  public recupereListePrets() {
     this.subscribeListepret =  this.pretsUsagerService.getListePret().subscribe(result => {
       this.dataSource = new PretDataSource(result);
      });
  }
  // traite la demande d eprolongation d'un pret : checkBox est cochée.
  onChecked(row ?: PretWeb, event ?: MatCheckboxChange) {

    if (row) {
        if (event.checked == true) {
          this.subscribeProlongePret =  this.pretsUsagerService.prolongePret(row).subscribe(
            (result: PretWeb) => {
              this.dataSource.pretWebs.find((x)=> {
                if (x.ouvrageIdouvrage == result.ouvrageIdouvrage) {
                  x.pretprolonge = result.pretprolonge;
                  x.dateEmprunt = result.dateEmprunt;
                }
              })
            });
        }
    }
  }
  //si le pret a deja été renouvellé,
  // la case à cocher (CheckBox) est cochée et désactivée
  // donc impossible de sélectionné à nouveau ce pret (renouvelable une et une seule fois: cahier des charges)
  isAlreadyRenewed(row: PretWeb) :boolean {
    let valRet:boolean=false;
    if (row) {
      if (row.pretprolonge ==1) {
              valRet=true
      }
    }
    return valRet;
  }
  // sur l'init, je récupère les prets de cet usager
  ngOnInit() {
    this.recupereListePrets();
  }

  // sur sortie composant suite modif du dom via un *ngIf, désinscription auprès des observables
  ngOnDestroy() {
    if(this.subscribeListepret != undefined)
      this.subscribeListepret.unsubscribe();
    if (this.subscribeProlongePret != undefined)
      this.subscribeProlongePret.unsubscribe();
  }
}
/*
Data source nécessaire avec table angular/angular material
 */
export class PretDataSource extends DataSource<PretWeb> {

  pretWebs: PretWeb[] =[];
  private _data: BehaviorSubject<PretWeb[]>;

  constructor(x : PretWeb[]) {
    super();
    this.pretWebs = x;
    this.data = new BehaviorSubject<PretWeb[]>(this.pretWebs);
  }

  //override car extends DataSource de angular/angular material
  connect(collectionViewer: CollectionViewer): Observable<PretWeb[] | ReadonlyArray<PretWeb>> {return this.data;}

  //override car extends DataSource de angular/angular material
  disconnect(collectionViewer: CollectionViewer): void {
    this.data.unsubscribe();
  }
  // pour ne pas répéter this. .... sur un observable
  get data(): BehaviorSubject<PretWeb[]> {return this._data;}
  set data(value: BehaviorSubject<PretWeb[]>) {this._data = value;}
}
/*
et voilà
 */

