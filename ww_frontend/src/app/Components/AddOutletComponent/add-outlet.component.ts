import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { User } from 'src/app/Models/User';
import { ProfileService } from 'src/app/Services/profile.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { OutletService } from 'src/app/Services/OutletService';
import { GooglePlaceModule } from 'ngx-google-places-autocomplete/ngx-google-places-autocomplete.module';


@Component({
  selector: 'app-add-outlet',
  templateUrl: './add-outlet.component.html',
  styleUrls: ['./add-outlet.component.css']
})
export class AddOutletComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private logger: NGXLogger,
    private profileSvc: ProfileService, private formBuilder: FormBuilder,
    private outLetSvc: OutletService, private router: Router) { }

  user!:User
  addOutletForm!: FormGroup
  apiKey: string = "AIzaSyAvWeaEpYdx0CRVg18W348eUT_ZTZl9z6w&"
  @ViewChild('storeName') addresstext: any;
  date = new Date().getFullYear();

  options: any = {
        types:['establishment'],
        componentRestrictions:{'country':['SG']},
        fields:['place_id','geometry','name']
  }



  ngOnInit(): void {

    this.profileSvc.getUser()
      .then(res=>{
        this.user = res
        this.logger.info(this.user)
      }).catch(error=>this.logger.info(error))

    this.addOutletForm = this.formBuilder.group({
      storeName: this.formBuilder.control<String>('',[Validators.required]),
      phoneNumber: this.formBuilder.control<String>('', [Validators.required, Validators.pattern('[0-9]*$'), Validators.minLength(8)]),
      unitNumber: this.formBuilder.control(null, [Validators.required]),
      outletPic: this.formBuilder.control(null,[Validators.required]),

    })
  }

  processAddOutletForm(){
    this.logger.info("Process add outlet form >>>>> ")

    const outletAddressDTO = this.addOutletForm.value as OutletAddressDTO
    outletAddressDTO.storeName = (<HTMLInputElement>document.getElementById('storeName')).value
    this.logger.info(outletAddressDTO)

    this.outLetSvc.saveOutlet(outletAddressDTO)
      .then(res=>{
        this.logger.info(res)
        this.router.navigate(['/outlet'])
      }).catch(error=>{this.logger.info(error)})
    


  }




}
