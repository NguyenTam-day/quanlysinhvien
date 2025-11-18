def closure_stack(X, FDs):
    closure = set(X)
    stack = list(X)
    while stack:
        attr = stack.pop()
        for left, right in FDs:
            if left.issubset(closure) and not right.issubset(closure):
                new_attrs = right - closure
                closure |= new_attrs
                stack.extend(new_attrs)
    return closure

def check_fd(X, Y, FDs):
    return set(Y).issubset(closure_stack(X, FDs))

def candidate_keys_stack(R, FDs):
    keys = []
    stack = [[attr] for attr in R]  # start with single attributes
    
    while stack:
        attrs = stack.pop()
        attrs_set = set(attrs)
        if closure_stack(attrs_set, FDs) == set(R):
            if not any(k.issubset(attrs_set) for k in keys):
                keys.append(attrs_set)
        else:
            for attr in R:
                if attr not in attrs_set:
                    new_attrs = attrs + [attr]
                    if set(new_attrs) not in [set(k) for k in stack]:
                        stack.append(new_attrs)
    return keys

def is_superkey(X, R, FDs):
    return closure_stack(X, FDs) == set(R)

def find_bcnf_violation(R, FDs):
    for left, right in FDs:
        if left.issubset(R) and not is_superkey(left, R, FDs):
            return (set(left), set(right))
    return None

def bcnf_decompose_stack(R, FDs):
    result = [set(R)]
    changed = True
    while changed:
        changed = False
        for relation in result.copy():
            violation = find_bcnf_violation(relation, FDs)
            if violation:
                left, right = violation
                A = left | right
                B = relation - (right - left)
                result.remove(relation)
                result.append(A)
                result.append(B)
                changed = True
                break
    return result

def equivalent_fd(FD1, FD2, FDs):
    X1, Y1 = FD1
    X2, Y2 = FD2
    return check_fd(X1, Y1, FDs) and check_fd(X2, Y2, FDs)