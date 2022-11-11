import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { User } from 'src/app/Models/User';
import { OutletService } from 'src/app/Services/OutletService';
import { ProfileService } from 'src/app/Services/profile.service';

@Component({
  selector: 'app-outlet',
  templateUrl: './outlet.component.html',
  styleUrls: ['./outlet.component.css']
})
export class OutletComponent implements OnInit {

  user!:User
  outlets!: OutletAddressDTO[]
  date = new Date().getFullYear();
  time = new Date().getHours();


  constructor(private profileSvc:ProfileService, private logger: NGXLogger,
    private router: Router, private outletSvc: OutletService) { }

  ngOnInit(): void {

    this.profileSvc.getUser()
      .then(res=>{
        this.user = res
        console.log(this.user)

      }).catch(error=>console.log(error))
    
  
      this.outletSvc.getOutlets()
        .then(res=>{
          this.logger.info(res)
          this.outlets = res
        })
      console.log(this.time)
  }

  addOutlet(){
   this.logger.info("create outlet clicked>>>")
   this.router.navigate(['/addOutlet',this.user.userId])
  }

  viewOutletProfile(addressId:string){
    this.logger.info("view outlet profile clicked >>>>> " + addressId)

    this.router.navigate(['/outletProfile', addressId])
  }

}
