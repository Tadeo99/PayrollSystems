import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsHighlightComponent } from './highlight.component';

@NgModule({
    declarations: [
        BsHighlightComponent
    ],
    imports     : [
        CommonModule
    ],
    exports     : [
        BsHighlightComponent
    ]
})
export class BsHighlightModule
{
}
