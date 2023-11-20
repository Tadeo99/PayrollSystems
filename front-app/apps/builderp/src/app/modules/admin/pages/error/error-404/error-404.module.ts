import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Error404Component } from './error-404.component';
import { error404Routes } from './error-404.routing';
import { TranslocoModule } from '@ngneat/transloco';
@NgModule({
    declarations: [
        Error404Component
    ],
    imports: [
        RouterModule.forChild(error404Routes),
        TranslocoModule,
    ]
})
export class Error404Module {
}
