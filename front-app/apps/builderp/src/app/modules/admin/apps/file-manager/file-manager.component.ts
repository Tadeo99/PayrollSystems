import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector     : 'ng-mf-file-manager',
    templateUrl    : './file-manager.component.html',
    encapsulation  : ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileManagerComponent
{
    /**
     * Constructor
     */
    constructor()
    {
    }
}
