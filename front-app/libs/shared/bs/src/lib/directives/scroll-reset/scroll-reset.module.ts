import { NgModule } from '@angular/core';
import { BsScrollResetDirective } from './scroll-reset.directive';

@NgModule({
    declarations: [
        BsScrollResetDirective
    ],
    exports: [
        BsScrollResetDirective
    ]
})
export class BsScrollResetModule {
}
