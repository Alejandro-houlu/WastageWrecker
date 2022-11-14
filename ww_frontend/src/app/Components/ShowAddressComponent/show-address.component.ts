import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NGXLogger } from 'ngx-logger';
import { OutletAndPromoDTO } from 'src/app/Models/OutletAndPromoDTO';
import { SubscriptionDTO } from 'src/app/Models/SubscriptionDTO';
import { SubscriptionService } from 'src/app/Services/SubscriptionService';
import { SearchResultComponent } from '../SearchResultComponent/search-result.component';

@Component({
  selector: 'app-show-address',
  templateUrl: './show-address.component.html',
  styleUrls: ['./show-address.component.css']
})
export class ShowAddressComponent implements OnInit {

  isSubbed!:SubscriptionDTO

  constructor(@Inject(MAT_DIALOG_DATA) public data:OutletAndPromoDTO,
  private dialog:MatDialog, private logger: NGXLogger,
  public dialogRef:MatDialogRef<SearchResultComponent>,
  private subSvc:SubscriptionService) { }

  ngOnInit(): void {

    this.subSvc.isSubscribed(this.data.addressId)
      .then(res=>{
        this.logger.info(res)
        this.isSubbed = res
      })

  }

  closeDialog(){
    this.logger.info("Close dialog clicked")
    this.dialog.closeAll()
  }

  followClicked(){
    this.dialogRef.close(this.data)

  }
  unFollowClicked(){

    this.subSvc.unsubscribe(this.data.addressId)
      .then(res=>{
        this.logger.info(res)
        this.dialog.closeAll()
      })

  }

}
