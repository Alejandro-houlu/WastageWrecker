import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { OutletAndPromoDTO } from 'src/app/Models/OutletAndPromoDTO';
import { PromotionDTO } from 'src/app/Models/PromotionDTO';
import { User } from 'src/app/Models/User';
import { OutletService } from 'src/app/Services/OutletService';
import { ProfileService } from 'src/app/Services/profile.service';
import { PromotionService } from 'src/app/Services/PromotionService';
import { AddPromotionComponent } from '../AddPromotionComponent/add-promotion.component';

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.css']
})
export class PromotionComponent implements OnInit {

  user!:User
  promotionListByOwner!:OutletAndPromoDTO
  address!: OutletAddressDTO
  date = new Date().getFullYear();
  promotionDTO!: PromotionDTO

  constructor(private logger: NGXLogger, private profileSvc:ProfileService,
    private router: Router, private promoSvc:PromotionService,
    private dialog: MatDialog, private outletSvc: OutletService) { }

  ngOnInit(): void {

    this.profileSvc.getUser()
      .then(res=>{
        this.logger.info('This is from profile component >>>>')
        this.logger.info(res)
        this.user = res
      }).catch(error=>{
        this.logger.info(error)
        this.router.navigate(['/home'])

        })

    this.promoSvc.getAllPromotionsAndAddressByUser()
        .then(res=>{
          this.logger.info(res)
          this.promotionListByOwner = res
        }).catch(error=>{this.logger.info(error)})
  }

  promoClicked(promotion:PromotionDTO, addressId:string){
    this.logger.info(promotion)
    const dislogRef = this.dialog.open(AddPromotionComponent,{width:"500px", disableClose:true, data:promotion })

    dislogRef.afterClosed().subscribe(res=>{
      this.logger.info('This is AFTER edit dialog closed')
      this.promotionDTO = res
      this.logger.info(this.promotionDTO)
      this.outletSvc.savePromotion(this.promotionDTO, addressId)
        .then(res=>{
          this.logger.info(res)
        }).catch(error=>this.logger.info(error))

        this.reload();
    })
  

  }

  reload(){
    location.reload()
  }



}
