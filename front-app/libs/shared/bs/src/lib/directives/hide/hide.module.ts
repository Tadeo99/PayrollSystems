import { NgModule } from '@angular/core';
import { BsHideDirective } from './hide.directive';

@NgModule({
    declarations: [
        BsHideDirective
    ],
    exports: [
        BsHideDirective
    ]
})
export class BsHideModule {
}
