import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { TranslocoModule } from '@ngneat/transloco';
import { BsCabeceraGrillaModule } from './cabeceragrilla';
import { BsFileUploaderModule } from './upload';
import { BsAvatarModule } from './avatar';
import { BsTitleFrmModule } from './titlefrm';
import { BsNoResultadosModule } from './noresultados';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        TranslocoModule,
        BsCabeceraGrillaModule,
        BsTitleFrmModule,
        BsNoResultadosModule,
        BsFileUploaderModule,
        BsAvatarModule
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        TranslocoModule,
        BsCabeceraGrillaModule,
        BsTitleFrmModule,
        BsNoResultadosModule,
        BsFileUploaderModule,
        BsAvatarModule

    ]
})
export class SharedModule {
}
