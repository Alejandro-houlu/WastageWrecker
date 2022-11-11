import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { LoginDTO } from 'src/app/Models/LoginDTO';
import { LoginService } from 'src/app/Services/LoginService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  sessionId!:string

  date = new Date().getFullYear();

  loginForm!:FormGroup

  constructor(private formBuilder: FormBuilder, private logger: NGXLogger,
    private loginSvc:LoginService, private router:Router, private http:HttpClient) { }

  ngOnInit(): void {

    this.loginForm = this.formBuilder.group({
      email: this.formBuilder.control<String>('',[Validators.required, Validators.email]),
      password: this.formBuilder.control<String>('',[Validators.required])
    })
  }

  processLoginForm(){

    const loginDTO = this.loginForm.value as LoginDTO
    this.logger.info(loginDTO)

    this.loginSvc.login(loginDTO)
      .then(res=>{
        this.logger.info(res)

        this.sessionId = res.sessionId
        this.logger.info(this.sessionId)
        sessionStorage.setItem('token',this.sessionId)
        this.logger.info(res.role)

        this.router.navigate(['/profile',res.userId])

      }).catch(error => {
        this.logger.info('>>>Error: ', error)
      })   


  }



}
