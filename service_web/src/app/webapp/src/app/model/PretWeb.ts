import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PretWeb {

  // tslint:disable:variable-name
  private _ouvrageIdouvrage: number;
  private _userIduser: number;
  private _pretprolonge: number;
  private _dateEmprunt: Date;
  private _auteur: string;
  private _titre: string;
  markChecked: boolean;

  constructor() {
    this.resetPretWeb();
  }

  public  resetPretWeb() {
    this.ouvrageIdouvrage = -1;
    this.userIduser = -1;
    this.dateEmprunt = null;
    this.auteur = '';
    this.titre = '';
  }

  get ouvrageIdouvrage(): number {
    return this._ouvrageIdouvrage;
  }

  set ouvrageIdouvrage(value: number) {
    this._ouvrageIdouvrage = value;
  }

  get userIduser(): number {
    return this._userIduser;
  }

  set userIduser(value: number) {
    this._userIduser = value;
  }

  get pretprolonge(): number {
    return this._pretprolonge;
  }

  set pretprolonge(value: number) {
    this._pretprolonge = value;
  }

  get dateEmprunt(): Date {
    return this._dateEmprunt;
  }

  set dateEmprunt(value: Date) {
    this._dateEmprunt = value;
  }

  get auteur(): string {
    return this._auteur;
  }

  set auteur(value: string) {
    this._auteur = value;
  }

  get titre(): string {
    return this._titre;
  }

  set titre(value: string) {
    this._titre = value;
  }

}



