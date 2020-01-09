import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class OuvrageWeb {
  private _titre:string;
  private _auteur:string;
  private _quantite:number;

  constructor(titre ?: string, auteur ?: string, quantite ?:number) {
    this.resetOuvrageWeb();
    if (titre != undefined)
      this.titre=titre;
    if (auteur != undefined)
      this.auteur =auteur;
    if (quantite != undefined)
      this.quantite= quantite;
  }

  private resetOuvrageWeb() {
    this.titre='';
    this.auteur='';
    this.quantite= -1;
  }


  get titre(): string {
    return this._titre;
  }

  set titre(value: string) {
    this._titre = value;
  }

  get auteur(): string {
    return this._auteur;
  }

  set auteur(value: string) {
    this._auteur = value;
  }

  get quantite(): number {
    return this._quantite;
  }

  set quantite(value: number) {
    this._quantite = value;
  }
}
