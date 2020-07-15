/* *************************************************************
* Composant recherche ouvrage diponibles (quantité > 0)
* le filtre sur quantité >0 est réalisé par le backend
* le composant ne recoit que des ouvrages disponibles
***************************************************************/
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {OuvrageWeb} from "../model/ouvrageWev";
import {OuvragesDisponiblesService} from "./ouvrages-disponibles.service";
import {BehaviorSubject, Observable, Subscription} from "rxjs";

import {FormBuilder, FormGroup} from "@angular/forms";
import {DataSource} from "@angular/cdk/collections";

@Component({
  selector: 'app-ouvrages-disponibles',
  templateUrl: './ouvrages-disponibles.component.html',
  styleUrls: ['./ouvrages-disponibles.component.css']
})

export class OuvragesDisponiblesComponent implements OnInit ,OnDestroy{

  //souscription à l'observable qui fournit la liste des ouvrages disponibles
  //sera utilsé sur le ngDestroy
  private subscribeOuvragesDispos:Subscription;

  private _titreOuvrage: string;
  private _auteurOuvrage: string;

  //transmis par app.component pour afficher ou pas la forme de recherche d'ouvrages
  @Input('afficheRecherche')
  private _isAfficheFormRecherche: boolean;

  // forme utilisée par le template pour gestion forme de saisie auteur/titre
  private ouvrageDisposForm :FormGroup;

  //forme reactive angular material , donc il faut un datasource , ici : auteur,titre,quantite
  private dataSource: OuvrageDataSource;

  //forme reactive angular material , donc il faut un variable avec la liste des champs du data source
  displayedColumns: string[] = [ 'auteur','titre', 'quantite'];

  // transmis par app.component pour affichage ou pas de la table contenant les ouvrages disponibles
  @Input()
  isAffichageOuvrageOk: boolean;

  //injection. FormBuilder dépend de angular/material
  constructor(private ouvragesDisponiblesService : OuvragesDisponiblesService,
              private fb: FormBuilder) {
    this.ouvrageDisposForm = this.fb.group({auteur:'' , titre:''});

  }
  //pour éviter de répeter this....get...
  get form_auteur() { return this.ouvrageDisposForm.get('auteur'); }
  get form_titre() { return this.ouvrageDisposForm.get('titre'); }

  ngOnInit() {
    this.titreOuvrage='';
    this.auteurOuvrage='';
  }

  // se désinscrit de l'observable
  ngOnDestroy(): void {
    if (this.subscribeOuvragesDispos != undefined) {
      this.subscribeOuvragesDispos.unsubscribe();
    }
  }

  // appel  l'observable  qui récupère la liste des ouvrages
  // si susccès , mise à jour de la datasource (observable mis à jour )
  onSubmit() {

    this.auteurOuvrage = this.form_auteur.value;
    this.titreOuvrage = this.form_titre.value;

    this.subscribeOuvragesDispos =  this.ouvragesDisponiblesService
      .getListeOuvragesDispos(this.auteurOuvrage,this.titreOuvrage)
      .subscribe(result => {
          this.dataSource = new OuvrageDataSource(result);
          this.isAfficheFormRecherche = false;}
    );
  }

  // TODO : faire un vrai cancel sur la forme de saisie qui "retourne" a app.component
  onCancel() {
    this.form_auteur.setValue('');
    this.form_titre.setValue('')
  }

  //same player shoot again
  onNouvelleRecherche() {
    this.dataSource.disconnect();
    this.isAfficheFormRecherche=true;

  }
  // et les getters setter
  get titreOuvrage(): string {
    return this._titreOuvrage;
  }

  set titreOuvrage(value: string) {
    this._titreOuvrage = value;
  }

  get auteurOuvrage(): string {
    return this._auteurOuvrage;
  }

  set auteurOuvrage(value: string) {
    this._auteurOuvrage = value;
  }

  get isAfficheFormRecherche(): boolean {
    return this._isAfficheFormRecherche;
  }

  set isAfficheFormRecherche(value: boolean) {
    this._isAfficheFormRecherche = value;
  }

}

/* *****************************************************************************
Data source nécessaire pour fct de angular/material - table des ouvrages dispos
 */
export class OuvrageDataSource extends DataSource<OuvrageWeb> {

  ouvrageWebs: OuvrageWeb[] =[];

  private data: BehaviorSubject<OuvrageWeb[]>;

  constructor(x : OuvrageWeb[]) {
    super();
    this.ouvrageWebs = x;
    this.data = new BehaviorSubject<OuvrageWeb[]>(this.ouvrageWebs);

  }
  // override nésessaire car extend DataSource....
  connect(): Observable < OuvrageWeb[] > {
    return this.data;
  }
  // override nésessaire car extend DataSource....
  disconnect() {
    this.ouvrageWebs = [];
    this.data.unsubscribe();
  }
}
/*
 et voilà
 */
