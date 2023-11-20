import { ChangeDetectionStrategy, Component, ViewEncapsulation } from '@angular/core';

@Component({
    selector: 'ng-mf-academy',
    templateUrl: './academy.component.html',
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AcademyComponent {
    /**
     * Constructor
     */
    constructor() {
        //
    }
}
