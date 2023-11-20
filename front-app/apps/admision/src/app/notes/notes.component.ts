import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'ng-mf-notes',
    templateUrl: './notes.component.html',
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotesComponent {
    /**
     * Constructor
     */
    constructor() {
        //
    }
}
