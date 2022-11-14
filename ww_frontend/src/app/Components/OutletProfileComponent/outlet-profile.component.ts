import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { PromotionDTO } from 'src/app/Models/PromotionDTO';
import { OutletService } from 'src/app/Services/OutletService';
import { AddPromotionComponent } from '../AddPromotionComponent/add-promotion.component';


@Component({
  selector: 'app-outlet-profile',
  templateUrl: './outlet-profile.component.html',
  styleUrls: ['./outlet-profile.component.css']
})
export class OutletProfileComponent implements OnInit {

  date = new Date().getFullYear();
  addressId!: string
  outlet!: OutletAddressDTO
  promotionDTO!: PromotionDTO
  promotionList!: PromotionDTO[]

  constructor(private outletSvc: OutletService, private router: Router,
    private activatedRoute: ActivatedRoute, private logger: NGXLogger,
    private dialog: MatDialog) { }

  ngOnInit(): void {

    this.addressId = this.activatedRoute.snapshot.params['addressId']

    this.outletSvc.getOutlet(this.addressId)
      .then(res=>{
        this.logger.info(res)
        this.outlet = res
      })
    
    this.outletSvc.getAllPromotions(this.addressId)
      .then(res=>{
        this.promotionList = res
        this.promotionList = this.promotionList.filter(v=>v.status==="ACTIVE")
        this.logger.info(res)
        this.logger.info(this.promotionList)
      }).catch(error=>this.logger.info(error))

    }

  openDialog(){
    console.log('Open dialog is clicked >>>>>')
    const dislogRef = this.dialog.open(AddPromotionComponent,{width:"500px", disableClose:true })

    dislogRef.afterClosed().subscribe(res=>{
      this.logger.info('This is AFTER dialog closed')
      this.promotionDTO = res
      this.logger.info(this.promotionDTO)
      this.outletSvc.savePromotion(this.promotionDTO, this.addressId)
        .then(res=>{
          this.logger.info(res)
        }).catch(error=>this.logger.info(error))

        this.reload();
    })
  }
  
  promoClicked(promotion:PromotionDTO){
    this.logger.info(promotion)
    const dislogRef = this.dialog.open(AddPromotionComponent,{width:"500px", disableClose:true, data:promotion })

    dislogRef.afterClosed().subscribe(res=>{
      this.logger.info('This is AFTER edit dialog closed')
      this.promotionDTO = res
      this.logger.info(this.promotionDTO)
      this.outletSvc.savePromotion(this.promotionDTO, this.addressId)
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

