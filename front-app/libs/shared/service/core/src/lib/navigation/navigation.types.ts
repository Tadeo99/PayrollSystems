import { IsActiveMatchOptions, Params, QueryParamsHandling } from '@angular/router';

export interface BsNavigationItem {
    id?: string;
    title?: string;
    subtitle?: string;
    type:
    | 'aside'
    | 'basic'
    | 'collapsable'
    | 'divider'
    | 'group'
    | 'spacer';
    hidden?: (item: BsNavigationItem) => boolean;
    active?: boolean;
    disabled?: boolean;
    tooltip?: string;
    link?: string;
    fragment?: string;
    preserveFragment?: boolean;
    queryParams?: Params | null;
    queryParamsHandling?: QueryParamsHandling | null;
    externalLink?: boolean;
    target?:
    | '_blank'
    | '_self'
    | '_parent'
    | '_top'
    | string;
    exactMatch?: boolean;
    isActiveMatchOptions?: IsActiveMatchOptions;
    function?: (item: BsNavigationItem) => void;
    classes?: {
        title?: string;
        subtitle?: string;
        icon?: string;
        wrapper?: string;
    };
    icon?: string;
    badge?: {
        title?: string;
        classes?: string;
    };
    children?: BsNavigationItem[];
    meta?: any;
}

export type BsVerticalNavigationAppearance =
    | 'default'
    | 'compact'
    | 'dense'
    | 'thin';

export type BsVerticalNavigationMode =
    | 'over'
    | 'side';

export type BsVerticalNavigationPosition =
    | 'left'
    | 'right';


export interface Navigation {
    compact: BsNavigationItem[];
    defaults: BsNavigationItem[];
    futuristic: BsNavigationItem[];
    horizontal: BsNavigationItem[];
}
