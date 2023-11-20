import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TitleFrmComponent } from './titlefrm.component';
import { TranslocoModule } from '@ngneat/transloco';
import { MatInputModule } from '@angular/material/input';

@NgModule({
    declarations: [
        TitleFrmComponent
    ],
    imports: [
        CommonModule,
        MatInputModule,
        TranslocoModule
    ],
    exports: [
        TitleFrmComponent
    ]
})
export class BsTitleFrmModule {
}
