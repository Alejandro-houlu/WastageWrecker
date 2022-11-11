import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/Models/User';
import { ProfileService } from 'src/app/Services/profile.service';
import { NGXLogger } from "ngx-logger";
import { LoginService } from 'src/app/Services/LoginService';
import { Router } from '@angular/router';
import { createPopper } from "@popperjs/core";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  authenticated!:boolean
  user!:User

  constructor(private profileSvc:ProfileService, private logger: NGXLogger,
    private loginSvc: LoginService, private router: Router) { }

  dropdownPopoverShow = false;
  @ViewChild("btnDropdownRef", { static: false }) btnDropdownRef!: ElementRef;
  @ViewChild("popoverDropdownRef", { static: false })
  popoverDropdownRef!: ElementRef;
  ngAfterViewInit() {
    createPopper(
      this.btnDropdownRef.nativeElement,
      this.popoverDropdownRef.nativeElement,
      {
        placement: "bottom-start",
      }
    );
  }
  toggleDropdown(event:Event) {
    event.preventDefault();
    if (this.dropdownPopoverShow) {
      this.dropdownPopoverShow = false;
    } else {
      this.dropdownPopoverShow = true;
    }
  }

  ngOnInit(): void {
    this.profileSvc.getUser()
      .then(res=>{
        this.user = res
        console.log(this.user)

      }).catch(error=>console.log(error))
  }

  logout(){
    sessionStorage.removeItem('token')
    this.router.navigate(['/home'])
  }


}
