package pe.buildsoft.erp.core.application.entidades.aas.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavigationItemVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String subtitle;
	private String type;// | 'aside' | 'basic' | 'collapsable' | 'divider' | 'group' | 'spacer';
	private boolean hidden;// ?: (item: BsNavigationItem) => boolean;
	private boolean active;
	private boolean disabled;
	private String tooltip;
	private String link;
	private String fragment;
	private boolean preserveFragment;
	private Object queryParams;
	private String queryParamsHandling;// 'merge' | 'preserve' | ''
	private boolean externalLink;
	private String target;// | '_blank' | '_self' | '_parent' | '_top' | string;
	private boolean exactMatch;
	private Object isActiveMatchOptions;
	private Object function;// ?: (item: BsNavigationItem) => void;
	private ClassesVO classes;
	private String icon;
	private BadgeVO badge;
	private List<NavigationItemVO> children = new ArrayList<>();
	private Object meta;
}
