import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/Models/User';
import { NGXLogger } from 'ngx-logger';
import { SignUpService } from 'src/app/Services/SignUpService';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm!:FormGroup
  date = new Date().getFullYear();

  constructor(private formBuilder: FormBuilder, private logger: NGXLogger,
    private signupSvc: SignUpService) { }

  ngOnInit(): void {
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
    user.role = "CUSTOMER"
    this.logger.info(user)

    this.signupSvc.saveCustomer(user)
      .then(res=>{
        this.logger.info(res)
      })

  }



}
