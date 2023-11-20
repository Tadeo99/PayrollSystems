import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsAlertModule } from '../alert';
import { BsCardModule } from '../card';
import { SharedModule } from '../shared.module';
import { SelectItemComponent } from './selectitem.component';

@NgModule({
    declarations: [
        SelectItemComponent
    ],
    imports: [
        SharedModule,
        MatButtonModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatProgressSpinnerModule,
        BsCardModule,
        BsAlertModule,

        MatCardModule,
        MatListModule,
        MatSelectModule,
        MatPaginatorModule,
        MatTooltipModule,
        MatProgressBarModule,
        MatSortModule
    ],
    exports: [
        SelectItemComponent
    ]
})
export class BsSelectItemModule {
}
