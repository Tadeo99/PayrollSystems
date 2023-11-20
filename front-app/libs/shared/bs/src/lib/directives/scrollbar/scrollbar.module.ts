import { NgModule } from '@angular/core';
import { BsScrollbarDirective } from './scrollbar.directive';

@NgModule({
    declarations: [
        BsScrollbarDirective
    ],
    exports: [
        BsScrollbarDirective
    ]
})
export class BsScrollbarModule {
}
