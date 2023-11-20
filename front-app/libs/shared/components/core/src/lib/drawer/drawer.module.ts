import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDrawerComponent } from './drawer.component';

@NgModule({
    declarations: [
        BsDrawerComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        BsDrawerComponent
    ]
})
export class BsDrawerModule {
}
