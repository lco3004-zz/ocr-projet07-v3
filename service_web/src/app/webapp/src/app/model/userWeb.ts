import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export  class UserWeb {
  // tslint:disable-next-line:variable-name
  private _idUser: number;
  // tslint:disable-next-line:variable-name
  private _username: string;
  // tslint:disable-next-line:variable-name
  private _password: string;


  constructor() {
    this.resetUserWeb();
  }

  public resetUserWeb(): void {
    this.username = '';
    this.password = '';
    this.idUser = -1;
  }

  get idUser(): number {
    return this._idUser;
  }

  set idUser(value: number) {
    this._idUser = value;
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

  public toString(): string {
    return '--UserWeb : << username: ' + this.username + ' , password: ' + this.password +
      '  , idUser: ' + this.idUser + ' >>';
  }
}
