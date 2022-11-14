import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';

import { AppComponent } from './app.component';
import { HomeComponent } from './Components/HomeComponent/home.component';
import { LoginComponent } from './Components/LoginComponent/login.component';
import { SignupComponent } from './Components/SignUpComponent/signup.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FootComponent } from './Components/FooterComponent/foot.component';
import { NavbarComponent } from './Components/NavbarComponent/navbar.component';
import { LoginService } from './Services/LoginService';
import { ProfileComponent } from './Components/ProfileComponent/profile.component';
import { ProfileService } from './Services/profile.service';
import { RequestInterceptor } from './request.interceptor';
import { AuthenticationGuard } from './authentication.guard';
import { SidebarComponent } from './Components/SidebarComponent/sidebar.component';
import { LineChartComponent } from './Components/LineChartComponent/line-chart.component';
import { BarChartComponent } from './Components/BarChartComponent/bar-chart.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { SignUpService } from './Services/SignUpService';
import { OutletComponent } from './Components/OutletComponent/outlet.component';
import { AddOutletComponent } from './Components/AddOutletComponent/add-outlet.component';
import { OutletService } from './Services/OutletService';
import { GooglePlaceModule } from 'ngx-google-places-autocomplete';
import { OutletProfileComponent } from './Components/OutletProfileComponent/outlet-profile.component';
import { AddPromotionComponent } from './Components/AddPromotionComponent/add-promotion.component';
import { PromotionComponent } from './Components/PromotionComponent/promotion.component';
import { PromotionService } from './Services/PromotionService';
import { SearchService } from './Services/SearchService';
import { SearchResultComponent } from './Components/SearchResultComponent/search-result.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { ShowAddressComponent } from './Components/ShowAddressComponent/show-address.component';
import { SubscriptionService } from './Services/SubscriptionService';

const appRoutes: Routes = [
  {path: '', component:HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup/:role', component: SignupComponent},
  {path: 'profile/:userId',component:ProfileComponent, canActivate:[AuthenticationGuard], data:{role:'OWNER'}},
  {path: 'outlet',component:OutletComponent, canActivate:[AuthenticationGuard]},
  {path: 'addOutlet/:userId', component:AddOutletComponent, canActivate:[AuthenticationGuard]},
  {path: 'outletProfile/:addressId', component:OutletProfileComponent, canActivate:[AuthenticationGuard]},
  {path: 'promotion',component:PromotionComponent, canActivate:[AuthenticationGuard]},
  {path: 'searchResult',component: SearchResultComponent, canActivate:[AuthenticationGuard]},
  {path:"**", redirectTo:'/', pathMatch:'full'},

]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    FootComponent,
    NavbarComponent,
    ProfileComponent,
    SidebarComponent,
    LineChartComponent,
    BarChartComponent,
    OutletComponent,
    AddOutletComponent,
    OutletProfileComponent,
    AddPromotionComponent,
    PromotionComponent,
    SearchResultComponent,
    ShowAddressComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    GooglePlaceModule,
    GoogleMapsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    LoggerModule.forRoot({
    serverLoggingUrl: '/api/logs',
    level: NgxLoggerLevel.DEBUG,
    serverLogLevel: NgxLoggerLevel.ERROR}),
    RouterModule.forRoot(appRoutes,{useHash: true})
  ],
  providers: [LoginService,ProfileService,SignUpService,OutletService, PromotionService,
    SearchService,SubscriptionService, {provide:HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
