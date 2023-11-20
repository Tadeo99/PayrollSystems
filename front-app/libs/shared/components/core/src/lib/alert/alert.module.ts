import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { BsAlertComponent } from './alert.component';

@NgModule({
    declarations: [
        BsAlertComponent
    ],
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule
    ],
    exports: [
        BsAlertComponent
    ]
})
export class BsAlertModule {
}
