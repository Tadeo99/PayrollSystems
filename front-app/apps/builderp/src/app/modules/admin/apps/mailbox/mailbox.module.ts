import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { QuillModule } from 'ngx-quill';
import { BsFindByKeyPipeModule } from '@bs/pipes/find-by-key';
import { BsNavigationModule } from '@bs/components/navigation';
import { BsScrollbarModule } from '@bs/directives/scrollbar';
import { BsScrollResetModule } from '@bs/directives/scroll-reset';
import { SharedModule } from '@ng-mf/shared/components/core';
import { MailboxComponent } from 'app/modules/admin/apps/mailbox/mailbox.component';
import { MailboxComposeComponent } from 'app/modules/admin/apps/mailbox/compose/compose.component';
import { MailboxDetailsComponent } from 'app/modules/admin/apps/mailbox/details/details.component';
import { MailboxEmptyDetailsComponent } from 'app/modules/admin/apps/mailbox/empty-details/empty-details.component';
import { MailboxListComponent } from 'app/modules/admin/apps/mailbox/list/list.component';
import { MailboxSettingsComponent } from 'app/modules/admin/apps/mailbox/settings/settings.component';
import { MailboxSidebarComponent } from 'app/modules/admin/apps/mailbox/sidebar/sidebar.component';
import { mailboxRoutes } from 'app/modules/admin/apps/mailbox/mailbox.routing';

@NgModule({
    declarations: [
        MailboxComponent,
        MailboxComposeComponent,
        MailboxDetailsComponent,
        MailboxEmptyDetailsComponent,
        MailboxListComponent,
        MailboxSettingsComponent,
        MailboxSidebarComponent
    ],
    imports: [
        RouterModule.forChild(mailboxRoutes),
        MatButtonModule,
        MatCheckboxModule,
        MatDialogModule,
        MatDividerModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatMenuModule,
        MatProgressBarModule,
        MatRippleModule,
        MatSelectModule,
        MatSidenavModule,
        QuillModule.forRoot(),
        BsFindByKeyPipeModule,
        BsNavigationModule,
        BsScrollbarModule,
        BsScrollResetModule,
        SharedModule,
    ]
})
export class MailboxModule
{
}
