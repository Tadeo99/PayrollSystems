import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FileUploaderComponent } from './file-uploader.component';
import { TranslocoModule } from '@ngneat/transloco';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
    declarations: [
        FileUploaderComponent
    ],
    imports: [
        CommonModule,
        MatIconModule,
        MatInputModule,
        TranslocoModule
    ],
    exports: [
        FileUploaderComponent
    ]
})
export class BsFileUploaderModule {
}
