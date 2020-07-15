/* *************************************************************
* service  recherche ouvrage diponibles (quantité > 0)
***************************************************************/

import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable, Subscriber} from "rxjs";
import {OuvrageWeb} from "../model/ouvrageWev";

/*
 pour injection dans composant
 todo : comprendre la portée du 'root'
 */
@Injectable({
  providedIn: 'root'
})
/*
 service
 */
export class OuvragesDisponiblesService {
  baseUrl = 'http://localhost:9091/gestionOuvrages';

  constructor(private http: HttpClient) {
  }
  // appel du backend
  /*
  * si ok, retourne la liste des ouvrages
  * si ko , retourne le code erreur http dans le tableau des ouvrages dispo
  *  le tableau contien alors un seul enregistrement dont  le titre est "pas d'ouvrage trouvé"
   */
  public getListeOuvragesDispos(auteur:string, titre:string): Observable<OuvrageWeb []> {
    if (auteur == undefined) auteur='%';
    if (titre == undefined) titre='%';
    const urlListePret = this.baseUrl + '/listeOuvrages'+'?auteur='+auteur+'&titre='+titre;
    return new Observable<OuvrageWeb []>(
      (stobserver: Subscriber<OuvrageWeb []>) => {
        this.http.get<OuvrageWeb []>(urlListePret,
          {headers: new HttpHeaders({'Content-Type' : 'application/json'}),
            withCredentials: true})
          .subscribe(
            (stresponse: OuvrageWeb []) => {stobserver.next(stresponse)},
            (error: HttpErrorResponse) => {
              const o: OuvrageWeb = new OuvrageWeb('....Pas d\' ouvrage trouvé.......','..........',0);
              const  ouvrageWebs: OuvrageWeb [] =[];
              ouvrageWebs.push(o);
              stobserver.next(ouvrageWebs);
            }
          );
      });
  }

}
/*
et voilà
 */
