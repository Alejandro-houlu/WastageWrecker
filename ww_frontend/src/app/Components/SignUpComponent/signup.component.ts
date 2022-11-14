import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/Models/User';
import { NGXLogger } from 'ngx-logger';
import { SignUpService } from 'src/app/Services/SignUpService';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm!:FormGroup
  date = new Date().getFullYear();
  role!:string

  constructor(private formBuilder: FormBuilder, private logger: NGXLogger,
    private signupSvc: SignUpService, private activatedRoute:ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {

    this.role = this.activatedRoute.snapshot.params['role']
    this.logger.info(this.role)

      this.signUpForm = this.formBuilder.group({
      username: this.formBuilder.control<String>('',[Validators.required]),
      email: this.formBuilder.control<String>('', [Validators.required, Validators.email]),
      phoneNumber: this.formBuilder.control<String>('', [Validators.required, Validators.pattern('[0-9]*$'), Validators.minLength(8)]),
      profilePic: this.formBuilder.control(null, [Validators.required]),
      password: this.formBuilder.control(null,[Validators.required]),
      verifyPassword: this.formBuilder.control(null,[Validators.required]),

    })
  }

  processCustomerSignUpForm(){

    const user = this.signUpForm.value as User
    if(this.role === 'customer'){
    user.role = "CUSTOMER"
    }else{
      user.role="OWNER"
    }

    this.logger.info(user)

    this.signupSvc.saveUser(user)
      .then(res=>{
        this.logger.info(res)
        this.router.navigate(['/login'])
      }).catch(error=>this.logger.info(error))

  }



}
