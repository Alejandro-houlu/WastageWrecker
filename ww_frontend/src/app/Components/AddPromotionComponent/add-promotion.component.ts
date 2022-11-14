import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { PromotionDTO } from 'src/app/Models/PromotionDTO';
import { OutletService } from 'src/app/Services/OutletService';
import { MatDialogRef } from '@angular/material/dialog';
import { OutletProfileComponent } from '../OutletProfileComponent/outlet-profile.component';
import { NonNullAssert } from '@angular/compiler';

@Component({
  selector: 'app-add-promotion',
  templateUrl: './add-promotion.component.html',
  styleUrls: ['./add-promotion.component.css']
})
export class AddPromotionComponent implements OnInit {

  public disabled = false;
  public showSpinners = true;
  public showSeconds = false;
  public touchUi = false;
  public enableMeridian = false;
  public stepHour = 1;
  public stepMinute = 1;
  public color: ThemePalette = 'primary';

  constructor(private formBuilder: FormBuilder, private dialog:MatDialog,
    private logger:NGXLogger, private outletSvc: OutletService, private activatedRoute: ActivatedRoute,
    public dialogRef:MatDialogRef<OutletProfileComponent>, @Inject(MAT_DIALOG_DATA) public data:PromotionDTO) { }

  addPromotionForm!: FormGroup

  ngOnInit(): void {

    
    if(this.data === null){

      console.log(this.data)
    this.addPromotionForm = this.formBuilder.group({
      itemName: this.formBuilder.control<string>('', [Validators.required]),
      description: this.formBuilder.control<String>('',[Validators.required]),
      startDate: this.formBuilder.control(null, [Validators.required]),
      endDate: this.formBuilder.control(null, [Validators.required]),
      status: this.formBuilder.control<String>('',[Validators.required]),
      price: this.formBuilder.control('',[Validators.required]),
      promoPic: this.formBuilder.control(null,[Validators.required]),
      promotionId: this.formBuilder.control(null)

    })
  } else{

    this.addPromotionForm = this.formBuilder.group({
      itemName: this.formBuilder.control<string>(this.data.itemName, [Validators.required]),
      description: this.formBuilder.control<String>(this.data.description,[Validators.required]),
      startDate: this.formBuilder.control(new Date(this.data.startDate.toString() + ', ' + this.data.startTime.toString()), [Validators.required]),
      endDate: this.formBuilder.control(new Date(this.data.endDate.toString() + ', ' + this.data.endTime.toString()), [Validators.required]),
      status: this.formBuilder.control<String>(this.data.status,[Validators.required]),
      price: this.formBuilder.control(this.data.price,Validators.required),
      promoPic: this.formBuilder.control(null),
    })
    this.addPromotionForm.addControl('promotionId', new FormControl(this.data.promotionId))
  }


  }

  processAddPromotionForm(){

    this.logger.info("Process add promotion form clicked >>>>>")
    this.logger.info(this.addPromotionForm)

    const promotion = this.addPromotionForm.value as PromotionDTO
    this.logger.info(promotion)

    // this.outletSvc.savePromotion(promotion)
    //   .then(res=>{
    //     this.logger.info(res)
    //   }).catch(error=>{
    //     this.logger.info(error)
    //   })

    this.dialogRef.close(promotion)
  }

  closeDialog(){
    this.logger.info("Close dialog clicked")
    this.dialog.closeAll()
  }

}
