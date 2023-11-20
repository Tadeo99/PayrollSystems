import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BsDrawerModule,SharedModule } from '@ng-mf/shared/components/core';
import { BsScrollbarModule } from '@ng-mf/shared/bs';
import { QuickChatComponent } from './quick-chat.component';

@NgModule({
    declarations: [
        QuickChatComponent
    ],
    imports: [
        RouterModule,
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        BsDrawerModule,
        BsScrollbarModule,
        SharedModule
    ],
    exports: [
        QuickChatComponent
    ]
})
export class QuickChatModule {
}
