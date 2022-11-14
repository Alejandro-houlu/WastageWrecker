import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/Models/User';
import { ProfileService } from 'src/app/Services/profile.service';
import { NGXLogger } from "ngx-logger";
import { OutletService } from 'src/app/Services/OutletService';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { PromotionDTO } from 'src/app/Models/PromotionDTO';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SearchService } from 'src/app/Services/SearchService';
import { OutletAndPromoDTO } from 'src/app/Models/OutletAndPromoDTO';
import { SubscriptionService } from 'src/app/Services/SubscriptionService';
import { ShowAddressComponent } from '../ShowAddressComponent/show-address.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userId!:string
  user!:User
  outlets!:OutletAddressDTO[]
  promotions!: PromotionDTO[]
  searchForm!: FormGroup

  constructor(private activatedRoute:ActivatedRoute,
    private profileSvc:ProfileService,
    private logger:NGXLogger,
    private router:Router, private outletSvc: OutletService,
    private formBuilder: FormBuilder, private searchSvc:SearchService,
    private subSvc: SubscriptionService,private dialog: MatDialog) { }

  date = new Date().getFullYear();
  outletAndPromoDTOList!: OutletAndPromoDTO[]

    // CUSTOMER METHODS

    options: any = {
        types:['establishment'],
        componentRestrictions:{'country':['SG']},
        fields:['place_id','geometry','name']
  }

  ngOnInit(): void {

    this.userId = this.activatedRoute.snapshot.params['userId']

    this.profileSvc.getUser()
      .then(res=>{
        this.logger.info('This is from profile component >>>>')
        this.logger.info(res)
        this.user = res
      }).catch(error=>{
        this.logger.info(error)
        this.router.navigate(['/home'])

        })

    this.outletSvc.getOutlets()
        .then(res=>{
          this.outlets = res
        }).catch(error=>this.logger.info(error))

    this.searchForm = this.formBuilder.group({
        search: this.formBuilder.control<string>('', [Validators.required])
    })

    this.subSvc.getSubscriptionsByUser()
      .then(res=>{
        this.logger.info('From getSubscriptionByUser in profile component')
        this.logger.info(res)
        this.outletAndPromoDTOList = res
      })

    
  } 

  processSearchForm(){
    this.logger.info('Search Clicked >>>>>')
    localStorage.removeItem('searchResult')

    this.logger.info(this.searchForm.value)
    this.logger.info((<HTMLInputElement>document.getElementById('search')).value)

    this.searchSvc.searchAddress((<HTMLInputElement>document.getElementById('search')).value)
      .then(res=>{
        this.logger.info(res)
        localStorage.setItem('searchResult', JSON.stringify(res))
      }
      ).then(res=>this.router.navigate(['/searchResult']))

  }

promoClicked(promo:OutletAndPromoDTO){

    this.logger.info(promo)
    const dialogRef = this.dialog.open(ShowAddressComponent,{width:"500px", disableClose:true, data:promo, })

}



}
