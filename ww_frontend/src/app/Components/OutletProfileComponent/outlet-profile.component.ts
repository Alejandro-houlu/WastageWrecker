import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { OutletAddressDTO } from 'src/app/Models/OutletAddressDTO';
import { OutletService } from 'src/app/Services/OutletService';

@Component({
  selector: 'app-outlet-profile',
  templateUrl: './outlet-profile.component.html',
  styleUrls: ['./outlet-profile.component.css']
})
export class OutletProfileComponent implements OnInit {

  date = new Date().getFullYear();
  addressId!: string
  outlet!: OutletAddressDTO

  constructor(private outletSvc: OutletService, private router: Router,
    private activatedRoute: ActivatedRoute, private logger: NGXLogger) { }

  ngOnInit(): void {

    this.addressId = this.activatedRoute.snapshot.params['addressId']

    this.outletSvc.getOutlet(this.addressId)
      .then(res=>{
        this.logger.info(res)
        this.outlet = res
      })


  }

}
