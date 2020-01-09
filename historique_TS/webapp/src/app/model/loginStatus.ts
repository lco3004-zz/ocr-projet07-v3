import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})

export class LoginStatus {

  private _isConnected : boolean;
  private _idSession : string;
  private _messageCnx: string;
  private _status: number;

  constructor() {
      this.resetLoginStatus();
  }

  public  resetLoginStatus() {
    this.isConnected= false;
    this.messageCnx='';
    this.idSession='';
    this.status = -1;
  }

  get status(): number {
    return this._status;
  }

  set status(value: number) {
    this._status = value;
  }

  get isConnected(): boolean {
    return this._isConnected;
  }

  set isConnected(value: boolean) {
    this._isConnected = value;
  }

  get idSession(): string {
    return this._idSession;
  }

  set idSession(value: string) {
    this._idSession = value;
  }

  get messageCnx(): string {
    return this._messageCnx;
  }

  set messageCnx(value: string) {
    this._messageCnx = value;
  }

  public toString() : string {
    return ' --LoginStatus : << isConnected: '+this.isConnected + ' , status: '+this.status +
      ' , messageCnx :' +this.messageCnx + ' , idSession: ' +this.idSession + ' >>';
  }

}



