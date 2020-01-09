/* *************************************************************
* service de recherche des ouvrages prétés à l'usager connecté
***************************************************************/
import {Injectable} from '@angular/core';
import {PretWeb} from '../model/PretWeb';
import {Observable, Subscriber} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';

/*
 pour injection dans le composant
 todo : comprendre la portée du 'root'
 */
@Injectable({
  providedIn: 'root'
})
/*
service
 */
export class PretsUsagerService {
  baseUrl = 'http://localhost:9091/gestionPrets';

  constructor(private http: HttpClient) {
  }
  /*
  appel au backend pour avoir la liste des prêts de l'usager connecté
  ...le backend connait l'usager actuellement connecté
  donc inutile de renvoyer user/password
  en retour : la liste des prets ou l'erreur Http si pb dans un enregistrement PretWeb
   */
  public getListePret(): Observable<PretWeb []> {
    const urlListePret = this.baseUrl + '/listePrets';
    return new Observable<PretWeb []>(
      (stobserver: Subscriber<PretWeb []>) => {
        this.http.get<PretWeb []>(urlListePret,
          {headers: new HttpHeaders({'Content-Type' : 'application/json'}),
            withCredentials: true})
          .subscribe( (stresponse: PretWeb []) => {stobserver.next(stresponse)} ,
            (error: HttpErrorResponse) => {
              const o: PretWeb = new PretWeb();
              o.titre= 'pas de pret trouvé';
              o.auteur =  'code erreur '+ error.status;
              // nécessaire pour que le tempalte ne permette pas de renouveler
              // un pret qui est ... une erreur (c'est typique de la bidouille avec effet de bord
              //mais bon... nobody is perfect
              o.pretprolonge=1;
              const  pretWebs: PretWeb [] =[];
              pretWebs.push(o);
              stobserver.next(pretWebs);
            }
          );
      });
  }

 /*
  demande de prolongation du pret selectioné,  au service Web backend
  pb avecle subscribe : le next est ok, mais pas le error quelque soitr le code http
  donc je passe l'info au composant qui gére si erreur Http
  TODO : comprendre pourquoi selon le coding, parfois le (error)=>{} fonctionne et parfois pas
  */
  public prolongePret(pretWeb: PretWeb) :Observable<PretWeb> {
    const urlProlongerPret = this.baseUrl + '/prolongerPret';
    const body: string = JSON.stringify({'ouvrageIdouvrage':pretWeb.ouvrageIdouvrage});

    return new Observable<PretWeb>(
      (observer: Subscriber<PretWeb>) => {
        this.http.put<PretWeb>(urlProlongerPret,body,
          {headers: new HttpHeaders({'Content-Type' : 'application/json'}), withCredentials: true})
          .subscribe( (response: PretWeb) => {
              if (response.userIduser != undefined) {
                observer.next(response);
              }
              else {
                observer.error(response);
              }
            }
          );
      });
  }
}
/*
et voilà
 */
