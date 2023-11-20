import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsFullscreenComponent } from './fullscreen.component';
import { CommonModule } from '@angular/common';

@NgModule({
    declarations: [
        BsFullscreenComponent
    ],
    imports     : [
        MatButtonModule,
        MatIconModule,
        MatTooltipModule,
        CommonModule
    ],
    exports     : [
        BsFullscreenComponent
    ]
})
export class BsFullscreenModule
{
}
