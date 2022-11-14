import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { LatLngDTO } from 'src/app/Models/LatLngDTO';
import { OutletAndPromoDTO } from 'src/app/Models/OutletAndPromoDTO';
import { User } from 'src/app/Models/User';
import { ProfileService } from 'src/app/Services/profile.service';
import { SearchService } from 'src/app/Services/SearchService';
import { MapInfoWindow, MapMarker } from '@angular/google-maps';
import { MatDialog } from '@angular/material/dialog';
import { ShowAddressComponent } from '../ShowAddressComponent/show-address.component';
import { DialogRef } from '@angular/cdk/dialog';
import { SubscriptionService } from 'src/app/Services/SubscriptionService';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  searchResult!: OutletAndPromoDTO[]
  searchedAddLatLng!:{}
  user!: User
  date = new Date().getFullYear();
  latlngDTO!: LatLngDTO
  display: any;
    center: google.maps.LatLngLiteral = {
        lat: 1.352083,
        lng: 103.819836
    };
  zoom = 13;
  @ViewChild(MapInfoWindow) infoWindow: MapInfoWindow | undefined;

  markerPositions: google.maps.LatLngLiteral[] = [];

  options: any = {
        types:['establishment'],
        componentRestrictions:{'country':['SG']},
        fields:['place_id','geometry','name']
  }

  constructor(private searchSvc: SearchService, private logger: NGXLogger,
    private router: Router, private profileSvc: ProfileService, private formBuilder: FormBuilder,
    private dialog: MatDialog,private subsSvc: SubscriptionService ) { }

  ngOnInit(): void {

    this.logger.info('on in it from search result >>>>>')

    this.profileSvc.getUser()
      .then(res=>{
        this.logger.info('This is from search result component >>>>')
        this.logger.info(res)
        this.user = res
        this.searchResult = JSON.parse(localStorage.getItem("searchResult") || "")
        this.searchedAddLatLng = {lat: this.searchResult[0].searchedAddLat, lng: this.searchResult[0].searchedAddLng}
        this.logger.info(this.searchedAddLatLng)
        this.searchResult.forEach(v=>{

        // this.latlngDTO  = {lat: v.lat, lng: v.lng, test: 'helloworld'}
        this.markerPositions.push(v)
        this.logger.info(v)

        })

        this.logger.info(this.searchResult)
        
      }).catch(error=>{
        this.logger.info(error)
        this.router.navigate(['/home'])

        })

    this.logger.info(this.latlngDTO)
   
  }

    moveMap(event: google.maps.MapMouseEvent) {
        if (event.latLng != null) this.center = (event.latLng.toJSON());
    }
    move(event: google.maps.MapMouseEvent) {
        if (event.latLng != null) this.display = event.latLng.toJSON();
    }
    markerOptions: google.maps.MarkerOptions = {
        draggable: false
    };

    openInfoWindow(marker: MapMarker) {
        if (this.infoWindow != undefined) {
          this.infoWindow.open(marker);
          
        }
    }

  promoClicked(promo:OutletAndPromoDTO){
    this.logger.info(promo)
    const dialogRef = this.dialog.open(ShowAddressComponent,{width:"500px", disableClose:true, data:promo, })

    dialogRef.afterClosed().subscribe(res=>{
      this.logger.info(res)
      const outletAndPromoDTO = res
      this.subsSvc.subscribe(outletAndPromoDTO.addressId)

    })


  }



  
    

    






}
