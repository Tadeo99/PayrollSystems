import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import {
    BsFullscreenModule, BsLoadingBarModule,
    BsNavigationModule
} from '@ng-mf/shared/components/core';
import { LanguagesModule } from '../../../../layout/common/languages/languages.module';
import { MessagesModule } from '../../../../layout/common/messages/messages.module';
import { NotificationsModule } from '../../../../layout/common/notifications/notifications.module';
import { QuickChatModule } from '../../../../layout/common/quick-chat/quick-chat.module';
import { SearchModule } from '../../../../layout/common/search/search.module';
import { ShortcutsModule } from '../../../../layout/common/shortcuts/shortcuts.module';
import { UserModule } from '../../../../layout/common/user/user.module';
import { SharedModule } from '@ng-mf/shared/components/core'
import { ClassyLayoutComponent } from './classy.component';

@NgModule({
    declarations: [
        ClassyLayoutComponent
    ],
    imports: [
        HttpClientModule,
        RouterModule,
        MatButtonModule,
        MatDividerModule,
        MatIconModule,
        MatMenuModule,
        BsFullscreenModule,
        BsLoadingBarModule,
        BsNavigationModule,
        LanguagesModule,
        MessagesModule,
        NotificationsModule,
        QuickChatModule,
        SearchModule,
        ShortcutsModule,
        UserModule,
        SharedModule
    ],
    exports: [
        ClassyLayoutComponent
    ]
})
export class ClassyLayoutModule {
}
