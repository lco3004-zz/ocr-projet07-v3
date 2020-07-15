import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class ReponseApiLogin {

  // tslint:disable-next-line:variable-name
  private _idSession: string;
  // tslint:disable-next-line:variable-name
  private _username: string;
  // tslint:disable-next-line:variable-name
  private _password: string;
  // tslint:disable-next-line:variable-name
  private _idUser: number;
  // tslint:disable-next-line:variable-name
  private _messageCnx: string;

  constructor() {
    this.resetResponseApiLogin();
  }

  public resetResponseApiLogin(): void {
    this.idSession = '';
    this.password = '';
    this.username = '';
    this.idUser = -1;
  }

  get messageCnx(): string {
    return this._messageCnx;
  }

  set messageCnx(value: string) {
    this._messageCnx = value;
  }

  get idSession(): string {
    return this._idSession;
  }

  set idSession(value: string) {
    this._idSession = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get idUser(): number {
    return this._idUser;
  }

  set idUser(value: number) {
    this._idUser = value;
  }


  public toString(): string {
    return '--ResponseApiLogin : << idSession: ' + this.idSession + ' , username: ' +
      this.username + ' , password: ' + this.password + ' , idUser: ' + this.idUser
      + ' , msgCnx: ' + this.messageCnx + ' >>';
  }

}
