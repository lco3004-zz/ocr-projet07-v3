/* *************************************************************
* Composant Authentification
***************************************************************/
import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Login} from '../model/login';
import {AuthService} from './auth.service';
import {LoginStatus} from '../model/loginStatus';
import {UserWeb} from '../model/userWeb';
import {Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

/*
* Ce composant est placé dans un [hidden] au niveau de app.component et non dans un *NgIf
* car  il faut pour accéder à "Logout" depuis app.component même quand ce template/composant
* n'est plus visible
* car *ngIf modifie le DOM ==> ngDestroy ==> la fonction "logout" n'est plus disponible ==> excpetion
* donc il faut utiliser [hidden] .
 */
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  providers: [AuthService]
})
export class AuthComponent implements OnInit, OnDestroy {

  /*
  *  conserve les souscription pour procéder à un  unscribe en sortie de composant
   */
  private subscribeAuth: Subscription;
  private subscribeLoginStatus: Subscription;
  private subscribeLogout: Subscription;

  private authForm :FormGroup;
  public model: Login;
  public infosLogout: string;

  /*
  * Le template est visible si isAffichage est vrai
  * Valorisation dans app.component.html
   */
  @Input()
  isAffichageOk:boolean;

  /*
  * Evenement envoyé à  app.component.ts lors de la déconnexion Usager
   */
  @Output()
  logoutTrigger: EventEmitter<Login> = new EventEmitter<Login>();


  /*
  * Evenement envoyé à  app.component.ts lors de la connexion Usager
   */
  @Output()
  loginSubmitDone: EventEmitter<string> = new EventEmitter<string>();

  /*
  * injection
  * de l'objet service et
  * de l'objet "Model" 'UserWeb' et
  * de l'objet "Model" 'LoginStatus et
  * de l'objet du framewaork Angular/Material FormBuilder
   */
   constructor(private authService: AuthService,
             private userWeb: UserWeb,
              // tslint:disable-next-line:variable-name
              private _loginStatus: LoginStatus,
              private fb: FormBuilder) {

    this.userWeb.resetUserWeb();
    this.isAffichageOk= false;
    this.model = new Login();

    /*
    * Creation des champs de la forme de saisie 'nom/mot de passe'
    * avec validation : 'champ non vide' c.a.d required AND minlenght de 1
     */

    this.authForm = this.fb.group(
       {username:'' , password:''},
       {Validators:[Validators.required, Validators.minLength(1)]}
       );
  }

  /*
  * évite de répéter this.aut...get ...
   */
  get form_username() { return this.authForm.get('username'); }
  get form_password() { return this.authForm.get('password'); }

  /*
  Sousciption à l'observable 'LoginStatus' qui contient les infos de cnx de l'usager
   */
  ngOnInit(): void {
    this._loginStatus.resetLoginStatus();
    this.subscribeLoginStatus = this.authService.chkIsConnected().subscribe(
      (resp: LoginStatus) => {this._loginStatus = resp;
      if(resp.status >0 && resp.status != 200) {
        this.authForm.get('username').patchValue('');
        this.authForm.get('password').patchValue('');
      }});
  }

  /*
  * désinsciption auprès de chaque observable
   */

  ngOnDestroy(): void {
    if (this.subscribeAuth != undefined)
      this.subscribeAuth.unsubscribe();

    if(this.subscribeLoginStatus != undefined)
      this.subscribeLoginStatus.unsubscribe();

    if(this.subscribeLogout != undefined)
      this.subscribeLogout.unsubscribe();

  }
  /*
  * sur bouton Cancel : message à app.component
  * app.componet va place isAffichageOk à faux, donc le template ne sera plus visible
   */
  onCancel() {
    this.userWeb.resetUserWeb();
    this.logoutTrigger.emit(this.model);
    this._loginStatus.resetLoginStatus();
  }
  /*
  * souscription à l'observable présent dans le service aut.service
  *  avec le username/password saisi par l'usager
  * le userWeb est mis à jour avec idUser...
  * et le message "ok je suis connécté" est émis vers app.component
   */
  onSubmit() {
    this.userWeb.resetUserWeb();

    this.model.username = this.form_username.value;
    this.model.password = this.form_password.value;

    this.subscribeAuth= this.authService.requestLogin(this.model).subscribe(valRet => {
      this.userWeb = valRet;
      this.loginSubmitDone.emit(valRet.username);
    });

  }

 /*
 * au revoir usager
 * souscription à l'observable présent dans le service aut.service
 * le userWeb est mis à jour avec idUser...
 * et le message "usgaer déconnecté" est émis vers app.component
 *
  */
  onClickLogout() {
    this.userWeb.resetUserWeb();

    this.subscribeLogout= this.authService.requestLogout().subscribe(valRet => this.infosLogout = valRet);

    this.logoutTrigger.emit(this.model);

  }
  /*
  getter sur l'attribut _loginStatus
   */
  get loginStatus(): LoginStatus {
    return this._loginStatus;
  }
}
/*
* Et voilà
*/
