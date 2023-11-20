import { NgModule } from '@angular/core';
import { ConceptoPdtSelectItemComponent } from './conceptopdtselect.component';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';
import { MatPaginatorModule } from '@angular/material/paginator';
import { SharedModule } from '@ng-mf/shared/components/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSortModule } from '@angular/material/sort';


@NgModule({
  imports: [
    SharedModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatPaginatorModule,
    MatTooltipModule,
    MatSortModule,
  ],
  declarations: [
    ConceptoPdtSelectItemComponent,

  ],
  exports: [
    ConceptoPdtSelectItemComponent
  ],
  providers: [

  ]
})
export class SharedConceptoPdtModule {

  static forRoot() {
    return {
      ngModule: SharedConceptoPdtModule,
      providers: []
    };
  }
}

