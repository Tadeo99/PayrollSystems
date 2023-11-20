import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BsAlertModule, BsHighlightModule, BsNavigationModule, SharedModule } from '@ng-mf/shared/components/core'
import { OtherComponentsComponent } from './other-components.component';
import { OverviewComponent } from './common/overview/overview.component';
import { LanguagesComponent } from './common/languages/languages.component';
import { MessagesComponent } from './common/messages/messages.component';
import { NotificationsComponent } from './common/notifications/notifications.component';
import { QuickChatComponent } from './common/quick-chat/quick-chat.component';
import { SearchComponent } from './common/search/search.component';
import { ShortcutsComponent } from './common/shortcuts/shortcuts.component';
import { UserComponent } from './common/user/user.component';
import { ApexChartsComponent } from './third-party/apex-charts/apex-charts.component';
import { QuillEditorComponent } from './third-party/quill-editor/quill-editor.component';
import { otherComponentsRoutes } from './other-components.routing';
import { BsScrollResetModule } from '@ng-mf/shared/bs';

@NgModule({
    declarations: [
        OtherComponentsComponent,
        OverviewComponent,
        LanguagesComponent,
        MessagesComponent,
        NotificationsComponent,
        QuickChatComponent,
        SearchComponent,
        ShortcutsComponent,
        UserComponent,
        ApexChartsComponent,
        QuillEditorComponent
    ],
    imports     : [
        RouterModule.forChild(otherComponentsRoutes),
        MatButtonModule,
        MatIconModule,
        MatSidenavModule,
        BsHighlightModule,
        BsAlertModule,
        BsNavigationModule,
        BsScrollResetModule,
        SharedModule
    ]
})
export class OtherComponentsModule
{
}
