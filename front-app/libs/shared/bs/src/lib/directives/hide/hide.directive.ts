import { Directive, ElementRef, Input, OnChanges, Renderer2 } from "@angular/core";

@Directive({
    // eslint-disable-next-line @angular-eslint/directive-selector
    selector: '[hide]'
})
export class BsHideDirective implements OnChanges {
    @Input() hide!: boolean;

    constructor(private renderer: Renderer2, private elRef: ElementRef) { }

    ngOnChanges() {
        if (this.hide) {
            this.renderer.setStyle(this.elRef.nativeElement, 'display', 'none !important');
        } /*else {
            this.renderer.setStyle(this.elRef.nativeElement, 'display', '');
        }*/
    }
}