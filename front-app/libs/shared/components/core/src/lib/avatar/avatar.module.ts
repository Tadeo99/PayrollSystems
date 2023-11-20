import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsAvatarComponent } from './avatar.component';
import { TranslocoModule } from '@ngneat/transloco';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
    declarations: [
        BsAvatarComponent
    ],
    imports: [
        CommonModule,
        MatIconModule,
        MatInputModule,
        TranslocoModule
    ],
    exports: [
        BsAvatarComponent
    ]
})
export class BsAvatarModule {
}
