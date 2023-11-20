import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsCardComponent } from './card.component';

@NgModule({
    declarations: [
        BsCardComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        BsCardComponent
    ]
})
export class BsCardModule {
}
