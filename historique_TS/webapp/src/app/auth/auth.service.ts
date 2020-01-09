/* *************************************************************
* Service Authentification
***************************************************************/
import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, Observer, Subscriber} from 'rxjs';

import {Login} from '../model/login';
import {UserWeb} from '../model/userWeb';

import {map} from 'rxjs/operators';
import {ReponseApiLogin} from '../model/respApiLogin';
import {LoginStatus} from '../model/loginStatus';

/*
 pour faire de l'injection dans auth.component
 'root' :  ? To Be Defined ...
 */
@Injectable({
  providedIn: 'root'
})
/*
 Service Authentiification
 */
export class AuthService {
  //l'URL du service WEB du backend
  baseUrl = 'http://localhost:9091/gestionUsagers';

  private _HTTP_OK = 200;

  //un petit getter
  get HTTP_OK(): number {
    return this._HTTP_OK;
  }

  //observable qui tient à jour l'état de l'authnetification
  public loginStatusObservable: Observable<LoginStatus>;
  // et son observer
  observer: Observer<LoginStatus>;
  // injection ...
  constructor(private http: HttpClient,
              private reponseApiLogin: ReponseApiLogin,
              private userWeb: UserWeb,
              private  loginStatus: LoginStatus) {
    // création de l'observable qui tient à jour l'état de l'authentificaiton
    this.loginStatusObservable = new Observable<LoginStatus>(obs => this.observer = obs);
  }
 // usuel
  public chkIsConnected(): Observable<LoginStatus> {
    return this.loginStatusObservable;
  }

  /*
  *  Sur réponse Http, quel est le statut de la réponse
  *  cas particulier : normalement il suffit de déclarer une fonction
  * qui est appelépar l'observable lors d'une erreur - ici erreur http
  * mais c'est le seul cas ou pas moyen de faire marcher (error:HttpResponseErro)=>{...}
  * alors que le service "recherche des préts en cours de l'usager" et
  * "rechercher des ouvrages dispos" fonctionnent parfaitement avec (error:HttpResponseErro)=>{...}
  * TODO : trouver pourquoi (error:HttpResponseErro)=>{...} ne fonctionne pas ici
   */
  private changeIsConnected(status: any) {

    if (this.observer !== undefined) {
      // tslint:disable-next-line:triple-equals
      if (typeof status == 'boolean') {
        const valx: boolean = status.valueOf();
        this.loginStatus.resetLoginStatus();
        this.loginStatus.isConnected = valx;

        // tslint:disable-next-line:triple-equals
      } else if (typeof status == 'number') {
        const i: number = status.valueOf();
        this.loginStatus.status = i;

        // tslint:disable-next-line:triple-equals
        if (i == this._HTTP_OK) {
          this.loginStatus.isConnected = true;
        } else {
          this.loginStatus.isConnected = false;
        }
      } else  if ( status instanceof HttpErrorResponse) {
        const resp: HttpErrorResponse = status;
        this.loginStatus.isConnected = false;
        this.loginStatus.status = resp.status;
        this.loginStatus.messageCnx = resp.message + ' ' + resp.statusText;
      }
      this.observer.next(this.loginStatus);
    }
  }

  //usuel
  private  chargeObjetsApplicatifs(reponseApiLogin: ReponseApiLogin): UserWeb {
    this.userWeb.username = reponseApiLogin.username;
    this.userWeb.password = reponseApiLogin.password;
    this.userWeb.idUser = reponseApiLogin.idUser;
    this.loginStatus.messageCnx = reponseApiLogin.messageCnx;
    this.loginStatus.idSession = reponseApiLogin.idSession;
    return this.userWeb;
  }

  // objet -> JSON
  private doBodyWithLoginData(login: Login): string {
    return JSON.stringify({username: login.username, password: login.password});
  }

  /*
  * Login appel effectif au service Web du Backend
   */
  public requestLogin(login: Login): Observable<UserWeb> {
    const body: string = this.doBodyWithLoginData(login);
    const urlLogin = this.baseUrl + '/loginUser';

    return new Observable<UserWeb>(
      (observer: Subscriber<UserWeb>) => {

        this.http.post<ReponseApiLogin>(urlLogin, body,
          {headers: new HttpHeaders({'Content-Type' : 'application/json'}),
            withCredentials: true})
          .pipe(map((response: ReponseApiLogin) => this.chargeObjetsApplicatifs(response)))
          .subscribe( (response: UserWeb) => {
                 observer.next(response);
                 this.changeIsConnected(Number(this._HTTP_OK)); },
            (error: HttpErrorResponse) => {
                 this.changeIsConnected(error); }
            );
      });
  }

  /*
  * Logout appel effectif au service Web du Backend
   */
  public requestLogout(): Observable<string> {
    const urlLogin = this.baseUrl + '/logoutUser';
    return new Observable<string>(
      (stobserver: Subscriber<string>) => {
        this.http.get<string>(urlLogin,  {headers: new HttpHeaders({'Content-Type' : 'application/json'}), withCredentials: true})
          .subscribe( (stresponse: string) => {stobserver.next(stresponse);
                                               this.changeIsConnected(Boolean(false)); },
            (error: HttpErrorResponse) => {this.changeIsConnected(error); }
          );
      });
  }
}
/*
* Et voilà
 */
