import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NGXLogger } from "ngx-logger";
import { firstValueFrom } from 'rxjs';
import { User } from '../Models/User';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http:HttpClient, private logger:NGXLogger) { }

  getUser():Promise<User>{

    return firstValueFrom(
      this.http.get<User>('/api/profile/')
    )

  }
}
