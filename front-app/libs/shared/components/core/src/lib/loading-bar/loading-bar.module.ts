import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { BsLoadingBarComponent } from './loading-bar.component';

@NgModule({
    declarations: [
        BsLoadingBarComponent
    ],
    imports: [
        CommonModule,
        MatProgressBarModule
    ],
    exports: [
        BsLoadingBarComponent
    ]
})
export class BsLoadingBarModule {
}
