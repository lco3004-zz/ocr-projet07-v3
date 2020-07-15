/* *************************************************************
* Composant principal, appelle dans index.html
* sert de dispatcher vers authentification, gestion des ouvages disponibles et
* gestion des prets de l'usager connecté
* ***************************************************************/
import {AfterViewInit, Component, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {AuthComponent} from './auth/auth.component';
import {Login} from "./model/login";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
/*
composant
 */
export class AppComponent implements AfterViewInit, OnChanges ,OnInit{

  private  title = 'Projet07';

  //nécessaire pour accèsder au logout qui est dans le composant auth.compoent
  @ViewChild(AuthComponent, { static: false })
  authComponent: AuthComponent;

  private isConnecter: boolean;

  private _username: string;

  private _switchAffichage: number;

  //evenement emis par auth.component sur logout, appel la fonction onAuth...logout
  //!! pas de handler du bouton logout ,ici, car le handler est dans le composant auth.component.ts
  //!! mais le bouton est dans ce template !
  // c'est pourquoi il faut que le composant auth.component reste disponible contrairement aux autres
  // components ==> il est [disabled] alors que les deux autres sont ngIfier donc sont supprimés du DOM et
  //leur méthode onDestroy est donc appelée.
  //
  public onAuthenticationLogout(event: Login) {
    this.switchAffichage=0;
    this.username = ' Visiteur';
  }

  //evenement emis par auth.component sur login, appel la fonction onAuth...login
  public onAuthenticationLoginDone(userName: string) {
    this.username = userName;
    this.switchAffichage=0;
  }

  constructor() {
    this.isConnecter = false;
  }

  //je sais plus
  ngAfterViewInit() {
    this.isConnecter = this.authComponent.loginStatus.isConnected;
  }

  //permet au template d'afficher le nom de l'usager connecté
  ngOnChanges(changes: SimpleChanges): void {
    this.isConnecter = this.authComponent.loginStatus.isConnected;
    if (this.isConnecter == true)
      this.username = this.authComponent.model.username;
  }

  //permet au template d'activer les boutons "prets" et "ouvrage"
  // de désactiver le bouton "login" puis d'activer le bouton logout
  //les boutons prets et ouvrage sont juste grisés : [hidden]..
  //alors que login et le logout son sous controle ngIf donc ils se remplacent
  // l'un et l'autre au même endroit de la page html car le DOM est modifié.
  // : plein de if car c'est de l'asynchrone et puis c'est joli cette pyramide de if
  public  isUsagerConnecte(): boolean {
    // tslint:disable-next-line:triple-equals
    if (this.authComponent != undefined) {
      // tslint:disable-next-line:triple-equals
      if (this.authComponent.loginStatus != undefined) {
        // tslint:disable-next-line:triple-equals
        if (this.authComponent.loginStatus.isConnected != undefined) {
          this.isConnecter = this.authComponent.loginStatus.isConnected;
        }
      }
    }
    return this.isConnecter;
  }
  // handler boutton - action click -
  onClickListePret() {
    this.switchAffichage = 1
  }
  // handler boutton - action click -
  onClickOuvrageDispos() {
    this.switchAffichage = 2
  }
  // handler boutton - action click -
  onClickLogin() {
    this.switchAffichage =3;
  }
  //usuel
  ngOnInit(): void {
    this.switchAffichage =0;
    this.username='';
  }

  get switchAffichage(): number {
    return this._switchAffichage;
  }

  set switchAffichage(value: number) {
    this._switchAffichage = value;
  }
  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

}
/*
et voilà
 */
