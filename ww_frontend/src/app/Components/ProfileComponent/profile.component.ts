import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/Models/User';
import { ProfileService } from 'src/app/Services/profile.service';
import { NGXLogger } from "ngx-logger";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  userId!:string
  user!:User
  constructor(private activatedRoute:ActivatedRoute,
    private profileSvc:ProfileService,
    private logger:NGXLogger,
    private router:Router) { }

  date = new Date().getFullYear();

  ngOnInit(): void {

    this.userId = this.activatedRoute.snapshot.params['userId']

    this.profileSvc.getUser()
      .then(res=>{
        this.logger.info('This is from profile component >>>>')
        this.logger.info(res)
        this.user = res
      }).catch(error=>
        this.router.navigate(['/home'])
      )
    
  }

}
