import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoResultadosComponent } from './noresultados.component';
import { TranslocoModule } from '@ngneat/transloco';

@NgModule({
    declarations: [
        NoResultadosComponent
    ],
    imports: [
        CommonModule,
        TranslocoModule
    ],
    exports: [
        NoResultadosComponent
    ]
})
export class BsNoResultadosModule {
}
