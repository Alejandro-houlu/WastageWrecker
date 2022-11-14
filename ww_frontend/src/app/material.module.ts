import { NgModule } from "@angular/core";
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import {  MatSelectModule} from '@angular/material/select'
import {  MatSidenavModule } from '@angular/material/sidenav'
import {  MatMenuModule } from '@angular/material/menu'
import {  MatListModule } from '@angular/material/list'
import { NgxMatFileInputModule } from "@angular-material-components/file-input";
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from "@angular/material/paginator";
import {MatCardModule} from '@angular/material/card';
import { MatAutocompleteModule} from '@angular/material/autocomplete';
import{ MatButtonToggleModule } from '@angular/material/button-toggle'
import{ MatDialogModule } from '@angular/material/dialog'
import {MatGridListModule} from '@angular/material/grid-list';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatRadioModule} from '@angular/material/radio';
import { MatNativeDateModule } from '@angular/material/core';
import { NgxMatDatetimePickerModule, NgxMatNativeDateModule, NgxMatTimepickerModule } from '@angular-material-components/datetime-picker';




const matModules: any[] = [
  MatToolbarModule, MatButtonModule, MatListModule,
  MatIconModule, MatInputModule, MatFormFieldModule,
  MatSelectModule, MatSidenavModule, MatMenuModule,
  NgxMatFileInputModule,MatTableModule,MatPaginatorModule,
  MatCardModule, MatAutocompleteModule, MatButtonToggleModule,
  MatDialogModule,MatGridListModule, MatDatepickerModule,
  MatRadioModule, MatNativeDateModule,    NgxMatDatetimePickerModule,
  NgxMatTimepickerModule,NgxMatNativeDateModule

]



@NgModule({
  imports: matModules,
  exports: matModules
})
export class MaterialModule {}